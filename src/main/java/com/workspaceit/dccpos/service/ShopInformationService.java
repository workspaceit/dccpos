package com.workspaceit.dccpos.service;

import com.workspaceit.dccpos.dao.ShopInformationDao;
import com.workspaceit.dccpos.entity.ShopInformation;
import com.workspaceit.dccpos.exception.EntityNotFound;
import com.workspaceit.dccpos.helper.FormFilterHelper;
import com.workspaceit.dccpos.validation.form.shop.ShopForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Matin on 4/25/2018.
 */
@Service
@Transactional
public class ShopInformationService {
    private ShopInformationDao shopDao;
    private TempFileService tempFileService;

    @Autowired
    public void setShopDao(ShopInformationDao shopDao){this.shopDao=shopDao;}

    @Autowired
    public void setTempFileService(TempFileService tempFileService) {
        this.tempFileService = tempFileService;
    }




    @Transactional
    public ShopInformation getShopInformation(){
        ShopInformation shop =  this.shopDao.getOne();
        /**
         * For Avoiding null pointer exception
         * */
        if(shop==null)shop = new ShopInformation();

        return shop;
    }


    public ShopInformation create(ShopForm shopForm) throws EntityNotFound {

        FormFilterHelper.doBasicFiler(shopForm);
        String imagePath = "";
        if(shopForm.getImageToken()!=null && shopForm.getImageToken()>0){
            imagePath = this.tempFileService.copyFileToCommonFolder(shopForm.getImageToken());
        }

        ShopInformation shop = this.getShopInformation();

        shop.setName(shopForm.getName());
        shop.setAddress(shopForm.getAddress());
        shop.setEmail(shopForm.getEmail());
        shop.setLogo(imagePath);
        shop.setPhone(shopForm.getPhone());

        this.save(shop);

        return shop;
    }



    private void save(ShopInformation shopInformation){
        this.shopDao.save(shopInformation);
    }

}
