package com.chengdu.jiq.common.exceptionhandler;

import com.chengdu.jiq.model.bo.exception.GlobalException;
import com.chengdu.jiq.model.bo.exception.ResponseCode;
import com.chengdu.jiq.model.bo.exception.ServerException;
import com.chengdu.jiq.model.bo.exception.StorageFileNotFoundException;
import com.chengdu.jiq.model.vo.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jiyiqin on 2017/9/16.
 *
 * @ControllerAdvice注解是一种控制器增强. 其将内部使用@ExceptionHandler、@InitBinder、@ModelAttribute注解的方法
 * 应用到所有的 @RequestMapping注解的方法。
 * 非常简单，不过只有当使用@ExceptionHandler最有用，另外两个用处不大
 */
@ControllerAdvice(basePackages = "com.chengdu.jiq.service")
public class PlatformControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformControllerAdvice.class);

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "Magical Sam");
    }

    /**
     * 全局异常捕捉处理
     *
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK) // 200
    @ExceptionHandler(value = GlobalException.class)
    @ResponseBody
    public ResponseData jsonErrorHandler(ServerException e) throws Exception {
        ResponseData r = new ResponseData();
        r.setMessage(e.getMessage());
        r.setCode(e.getCode());
        r.setData(null);
        r.setStatus(false);
        return r;
    }

    /**
     * 全局异常捕捉处理
     *
     * @param e
     * @return
     */
//    @ResponseStatus(HttpStatus.OK) // 200
//    @ExceptionHandler(value = Exception.class)
//    @ResponseBody
//    public ResponseData handleException(Exception e) {
//        ResponseData r = new ResponseData();
//        r.setMessage("Server Inner Error");
//        r.setCode(ResponseCode.SERVER_ERROR_CODE.getCode());
//        r.setData(null);
//        r.setStatus(false);
//        return r;
//    }

    /**
     * 全局异常捕捉处理
     *
     * @param exc
     * @return
     */
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
