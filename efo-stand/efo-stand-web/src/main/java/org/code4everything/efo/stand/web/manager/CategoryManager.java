package org.code4everything.efo.stand.web.manager;

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

    private final CategoryRepository categoryRepository;

    /**
     * 分类编号与分类的映射
     */
    private final Map<Integer, CategoryNode> map = new HashMap<>(32);

    /**
     * 顶级节点
     */
    private CategoryNode root = new CategoryNode(null);

    public CategoryManager(CategoryRepository categoryRepository) {this.categoryRepository = categoryRepository;}

    /**
     * 项目启动时初始化数据
     */
    @PostConstruct
    public void init() {
        addChildren(root);
    }

    private void addChildren(CategoryNode node) {
        if (Objects.isNull(node)) {
            return;
        }
        List<CategoryDO> list = categoryRepository.getByParentId(node.getId());
        for (CategoryDO category : list) {
            CategoryNode aNode = new CategoryNode(category);
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

        CategoryNode(CategoryDO current) {
            this.current = current;
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
    }
}
