package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.dao.ProductDao;
import com.workspaceit.dccpos.entity.Category;
import com.workspaceit.dccpos.entity.Product;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.FormFilterHelper;
import com.workspaceit.dccpos.validation.form.product.ProductCreateForm;
import com.workspaceit.dccpos.validation.form.product.ProductUpdateForm;
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
    public List<Product> getAll(int limit, int offset){
        offset = (offset-1)*limit;
        return this.productDao.findAll(limit,offset);
    }
    @Transactional
    public List<Product> getByCategoryId(int categoryId,int limit, int offset){
        offset = (offset-1)*limit;
        return this.productDao.findByCategoryId(categoryId,limit,offset);
    }
    @Transactional
    public long getByCategoryIdCount(int categoryId){
        return this.productDao.findByCategoryIdCount(categoryId);
    }

    @Transactional
    public long getTotalRowCount(){
        return this.productDao.findAllRowCount(Product.class);
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
    public Product  getByBarcodeAndNotById(int id,String barcode){
        return this.productDao.findByBarcodeAndNotById(id,barcode);
    }


    @Transactional
    public List<Product> getByNameLike(String nameLike,int limit, int offset){
        offset = (offset-1)*limit;
        return this.productDao.findByNameLike(limit,offset,nameLike);
    }
    @Transactional
    public long getByNameLikeCount(String nameLike){
        return this.productDao.findByNameLikeCount(nameLike);
    }
    public Product create(ProductCreateForm productCreateForm) throws EntityNotFound {

        FormFilterHelper.doBasicFiler(productCreateForm);

        Category category =(productCreateForm.getCategoryId()!=null)?
                            this.categoryService.getCategory(productCreateForm.getCategoryId()):null;
        int weight =  productCreateForm.getWeight()!=null?productCreateForm.getWeight():0;
        String imagePath = "";

        if(productCreateForm.getImageToken()!=null && productCreateForm.getImageToken()>0){
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
        FormFilterHelper.doBasicFiler(productUpdateForm);

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
    public void update(Product product){
        this.productDao.update(product);
    }
}