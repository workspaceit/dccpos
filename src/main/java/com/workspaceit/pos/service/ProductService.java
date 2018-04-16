package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.ProductDao;
import com.workspaceit.pos.entity.Category;
import com.workspaceit.pos.entity.Product;
import com.workspaceit.pos.exception.EntityNotFound;
import com.workspaceit.pos.validation.form.product.ProductCreateForm;
import com.workspaceit.pos.validation.form.product.ProductUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private ProductDao productDao;
    private CategoryService categoryService;
    private TempFileService tempFileService;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Autowired
    public void setTempFileService(TempFileService tempFileService) {
        this.tempFileService = tempFileService;
    }

    @Transactional
    public List<Product> getAll(){
        return this.productDao.findAll();
    }

    @Transactional
    public Product getById(int id){
        return this.productDao.findById(id);
    }
    @Transactional
    public Product getProduct(int id) throws EntityNotFound {
        Product product =  this.productDao.findById(id);
        if(product==null)throw new EntityNotFound("Product not found by id :"+id);

        return product;
    }

    @Transactional
    public Product  getByBarcode(String barcode){
        return this.productDao.findByBarcode(barcode);
    }

    @Transactional
    public List<Product> getByNameLike(String nameLike){
        return this.productDao.findByNameLike(nameLike);
    }
    public Product create(ProductCreateForm productCreateForm) throws EntityNotFound {

        Category category =(productCreateForm.getCategoryId()!=null)?
                            this.categoryService.getCategory(productCreateForm.getCategoryId()):null;
        int weight =  productCreateForm.getWeight()!=null?productCreateForm.getWeight():0;
        String imagePath = "";

        if(productCreateForm.getImageToken()!=null || productCreateForm.getImageToken()>0){
            imagePath = this.tempFileService.copyFileToCommonFolder(productCreateForm.getImageToken());
        }

        Product product = new Product();

        product.setName(productCreateForm.getName());
        product.setCategory(category);
        product.setWeight(weight);
        product.setWeightUnit(productCreateForm.getWeightUnit());
        product.setImage(imagePath);
        product.setBarcode(productCreateForm.getBarcode());
        product.setTotalAvailableQuantity(0);

        this.save(product);

        return product;
    }
    public Product update(int id,ProductUpdateForm productUpdateForm) throws EntityNotFound {

        Product product = this.getProduct(id);

        Category category =  product.getCategory();

        int categoryId = (productUpdateForm.getCategoryId()!=null)?productUpdateForm.getCategoryId():0;
        int currentCategoryId = (product.getCategory()!=null)?product.getCategory().getId():0;

        if(categoryId!=currentCategoryId){
            if(categoryId>0){
                category = this.categoryService.getCategory(categoryId);
            }else{
                category = null;
            }
        }


        int weight =  productUpdateForm.getWeight()!=null?productUpdateForm.getWeight():0;

        String imagePath = product.getImage();
        if(productUpdateForm.getImageToken()!=null && productUpdateForm.getImageToken()>0){
            imagePath = this.tempFileService.copyFileToCommonFolder(productUpdateForm.getImageToken());
        }


        product.setName(productUpdateForm.getName());
        product.setCategory(category);
        product.setWeight(weight);
        product.setWeightUnit(productUpdateForm.getWeightUnit());
        product.setImage(imagePath);
        product.setBarcode(productUpdateForm.getBarcode());

        this.save(product);

        return product;
    }
    private void save(Product product){
        this.productDao.save(product);
    }
    private void update(Product product){
        this.productDao.update(product);
    }
}