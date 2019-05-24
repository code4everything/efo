package org.code4everything.efo.stand.web.manager;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.code4everything.efo.stand.dao.domain.CategoryDO;
import org.code4everything.efo.stand.dao.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author pantao
 * @since 2019/5/24
 **/
@Slf4j
@Component
public class CategoryManager {

    // TODO: 2019/5/24 更新分类节点

    private final CategoryRepository categoryRepository;

    /**
     * 方便直接通过分类编号找到分类节点
     */
    private final Map<Integer, CategoryNode> map = new HashMap<>(32);

    /**
     * 顶级节点
     */
    private CategoryNode root = new CategoryNode();

    public CategoryManager(CategoryRepository categoryRepository) {this.categoryRepository = categoryRepository;}

    public List<String> listParents(Integer categoryId) {
        List<String> categories = new LinkedList<>();
        CategoryNode child = null;
        CategoryNode parent = null;
        // 从下往上一级一级的查找分类
        while (categoryId > 0) {
            boolean shouldBreak = false;
            // 从映射表中获取
            parent = map.get(categoryId);
            if (Objects.isNull(parent)) {
                // 从数据库里面查找，并添加到多叉树中
                CategoryDO category = categoryRepository.getById(categoryId);
                parent = new CategoryNode(category, root);
                map.put(categoryId, parent);
            } else {
                // 如果映射表存在，那么就跳出循环
                shouldBreak = true;
            }

            // 将分类名添加到列表中
            categories.add(0, parent.current.getName());

            if (ObjectUtil.isNotNull(child)) {
                // 更新节点信息
                child.parent = parent;
                parent.addChild(child);
            }

            if (shouldBreak) {
                break;
            }

            child = parent;
            categoryId = parent.current.getId();
        }
        // 从多叉树里面查找
        while (ObjectUtil.isNotNull(parent) && parent.hasValidParent()) {
            categories.add(0, parent.current.getName());
            parent = parent.parent;
        }
        return categories;
    }

    /**
     * 项目启动时初始化数据
     */
    @PostConstruct
    public void init() {
        addChildren(root);
    }

    /**
     * 从顶级分类开始，以深度优先的方式构建分类多叉树
     */
    private void addChildren(CategoryNode node) {
        if (Objects.isNull(node)) {
            return;
        }
        List<CategoryDO> list = categoryRepository.getByParentId(node.getId());
        for (CategoryDO category : list) {
            CategoryNode aNode = new CategoryNode(category, node);
            map.put(category.getId(), aNode);
            node.addChild(aNode);
            addChildren(aNode);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    class CategoryNode {

        CategoryDO current;

        CategoryNode parent;

        List<CategoryNode> children;

        CategoryNode(CategoryDO current, CategoryNode parent) {
            this.current = current;
            this.parent = parent;
        }

        void addChild(CategoryNode node) {
            if (Objects.isNull(children)) {
                children = new ArrayList<>();
            }
            children.add(node);
        }

        Integer getId() {
            return Objects.isNull(current) ? 0 : current.getId();
        }

        /**
         * 顶级节点不是有效的分类
         */
        boolean hasValidParent() {
            return ObjectUtil.isNotNull(parent) && parent.getId() > 0;
        }
    }
}
