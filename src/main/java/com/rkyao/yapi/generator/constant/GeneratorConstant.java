package com.rkyao.yapi.generator.constant;

/**
 * 常量类
 *
 * @author yaorongke
 * @date 2022/5/22
 */
public class GeneratorConstant {

    /**
     * 路径分隔符
     */
    public static final String SEPARATOR = "\\";

    /**
     * Java类文件后缀
     */
    public static final String JAVA_SUFFIX = ".java";

    /**
     * 文件保存目录
     */
    public static final String OUTPUT = "output";

    public static final String BASE_CREATE_PATH=OUTPUT + SEPARATOR + "%s";
    /**
     * controller class文件保存目录
     */
    public static final String CONTROLLER_PATH = OUTPUT + SEPARATOR + "%s";
    /**
     * FEIGN class文件保存目录
     */
    public static final String FEIGN_PATH = OUTPUT + SEPARATOR + "%s";
    /**
     * service class文件保存目录
     */
    public static final String SERVICE_PATH = OUTPUT + SEPARATOR + "%s";

    /**
     * impl class文件保存目录
     */
    public static final String IMPL_PATH = OUTPUT + SEPARATOR + "%s";

    /**
     * entity class文件保存目录
     */
    public static final String ENTITY_PATH = OUTPUT + SEPARATOR + "%s";
    public static final String ENTITY_DTO_PATH = ENTITY_PATH+SEPARATOR+"%s"+"Dto";
    public static final String ENTITY_VO_PATH = ENTITY_PATH+SEPARATOR+"%s"+"Vo";
    /**
     * controller ftl文件路径
     */
    public static final String CONTROLLER_FTL = "/freemarker/controller.ftl";
    /**
     * Feign ftl文件路径
     */
    public static final String FEIGN_FTL = "/freemarker/feignClient.ftl";
    /**
     * service ftl文件路径
     */
    public static final String SERVICE_FTL = "/freemarker/service.ftl";

    /**
     * controllimpler ftl文件路径
     */
    public static final String IMPL_FTL = "/freemarker/impl.ftl";

    /**
     * entity ftl文件路径
     */
    public static final String ENTITY_FTL = "/freemarker/entity.ftl";

}
