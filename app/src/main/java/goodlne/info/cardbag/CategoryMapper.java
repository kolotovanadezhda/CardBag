package goodlne.info.cardbag;

import java.util.ArrayList;
import java.util.List;

public final class CategoryMapper {
    public static Category categoryMap2Data(CategoryRealm categoryRealm) {
        Category category = new Category();
        category.setId(categoryRealm.getId());
        category.setName(categoryRealm.getName());
        return category;
    }
    public static List<CategoryRealm> map2RealmList(List<Category> categories) {
        List<CategoryRealm> realmList = new ArrayList<>();
        for(Category category : categories){
            CategoryRealm categoryRealm = new CategoryRealm();
            categoryRealm.setId(category.getId());
            categoryRealm.setName(category.getName());
            realmList.add(categoryRealm);
        }

        return realmList;
    }
    public static List <Category> map2DataList(List<CategoryRealm> realmList) {
        List<Category> categories = new ArrayList<>();
        for(CategoryRealm categoryRealm : realmList) {
            Category category = new Category(
                    categoryRealm.getId(),
                    categoryRealm.getName()
            );
            categories.add(category);
        }
        return categories;

    }
    public static CategoryRealm categoryMap2Realm(Category category) {
        CategoryRealm categoryRealm = new CategoryRealm();
        categoryRealm.setId(category.getId());
        categoryRealm.setName(category.getName());
        return categoryRealm;
    }
}
