package demo.controller;

import demo.vo.People;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
@RequestMapping("/test")
public class Controller {

    /**
     * 测试必填参数校验
     * 请求接口: http://localhost:8080/test/
     * 参数: {"id":1000,"name":"小小","age":18,"sex":"男","address":"深圳","phoNum":"187-0000-8973","email":"@163.com"}
     *
     * 性别和地址 必填  详情见 people VO
     * 测试参数 {"id":1000,"name":"小小","age":18,"phoNum":"187-0000-8973","email":"@163.com"}
     *
     * @param people people
     * @param bindingResult bindingResult
     * @return ResponseEntity<People>
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Object> test(@Valid @RequestBody People people, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            fieldErrorList.stream().forEach(fieldError -> {
                System.out.println(fieldError.getField() + ":" + fieldError.getDefaultMessage());
                // 控制台打印
                // address:地址不能为空
                // sex:性别不能为空
            });
        }
        return ResponseEntity.ok().body(people);
    }

    @GetMapping("/")
    public ResponseEntity<Object> test() {
        // 测试参数 比如 所有字段都必填
        // People people = People.builder().id(1000L).address("深圳").age(18).email("@163.com").sex("男").phoNum("187-0000-8973").build();
        People people = People.builder().build();
        Method[] methods = people.getClass().getDeclaredMethods();
        Arrays.stream(methods).
                filter(method -> Pattern.compile("get(\\p{javaUpperCase}\\w*)").matcher(method.getName()).matches())
                .forEach(method -> {
                    try {
                        // method.getGenericReturnType()
                        if (method.getReturnType().toString().equals("class java.lang.String")) {
                            String value = (String) method.invoke(people);
                            if (null == value || value.trim().isEmpty()) {
                                System.out.println(method.getName().substring(3) + "为空");
                            }
                        }
                        if (method.getReturnType().toString().equals("int")) {
                            int value = (int) method.invoke(people);
                        }
                        if (method.getReturnType().toString().equals("long")) {
                            long value = (long) method.invoke(people);
                        }
                    } catch (Exception ex) {
                    }
                });
        // 控制台打印
        // Address为空
        // Name为空
        // Sex为空
        // Email为空
        // PhoNum为空
        return ResponseEntity.ok().body(people);
    }
 // PowerMock用法总结

    // 类头部引入这两个注解
        // @RunWith(PowerMockRunner.class)
        // @PowerMockIgnore("javax.management.*")

    // @InjectMocks 测试的类 创建实例 @Mock 注解的会注入实例种
    // @Mock 测试的类 引用到的类 Mock注入进来 然后进行模拟行为和结果

    /**
     * 语法介绍
     */
    // 1. do...when...then... 这种语法一般是模拟某个方法的行为 结果
    // 2. Mockito.any() 参数
    // 3. Mockito.verify() 验证
    // 4. fail(异常)


    /**
     * 场景： 无返回值/抛异常的方法 行为和结果
     */
    // 1.方法里面加上
    // PowerMockito.doNothing().when(对象).方法(入参);
    // PowerMockito.doThrow(异常实例).when(对象).方法(入参);
    // PowerMockito.when(对象.方法(入参)).thenThrow(异常实例);



    /**
     * 场景： 模拟静态类
     */
    // 1.类头部加上 @PrepareForTest({静态类.class})
    // 2.方法里面加上
        // PowerMockito.mockStatic(静态类.class)
        // PowerMockito.when(静态类.方法名(参数1,参数2)).thenReturn(结果1).thenReturn(结果2);
            // 注 结果1 为第一次调用返回的结果 结果2 为第二次调用的时候返回的结果


    /**
     * 场景： 模拟方法内部 有new对象去调用方法 的行为和结果
     */
    // 1.类头部加上 @PrepareForTest({new的类.class})
    // 2.方法里面加上
    //  无参构造函数的模拟 PowerMockito.whenNew(new的类.class).withNoArguments().thenReturn(new出来的对象);
    //  有参构造函数的模拟 PowerMockito.whenNew(new的类.class).withArguments(参数1, 参数2).thenReturn(new出来的对象);
    // PowerMockito.when(new出来的对象.方法名(参数1,参数2)).thenReturn(结果1)
    //

    /**
     * 场景： 模拟方法内部 有局部引用 去调用方法 的行为和结果
     */
    // 1.方法里面加上
    // 引用的类 引用的类对象 = PowerMockito.mock(引用的类.class);
    // 无参构造函数的模拟 PowerMockito.whenNew(引用的类.class).withNoArguments().thenReturn(引用的类对象);
    // 有参构造函数的模拟 PowerMockito.whenNew(引用的类.class).withArguments(参数1, 参数2).thenReturn(引用的类对象);
    // PowerMockito.when(引用的类对象.方法名(参数1,参数2)).thenReturn(结果1)

    /**
     * 场景： 模拟Final类调用私有方法
     */
    // 1.方法里面加上
    // final类 final类对象 = spy(new final类());
    // PowerMockito.when(私有类对象, 私有类的私有方法, 入参).thenReturn(返回值);
    // 无返回值 PowerMockito.doNothing().when(私有类对象, 私有类的私有方法, 入参);

    /**
     * 场景： 模拟根据不同参数返回不同结果
     */
    // 1.方法里面加上
    // PowerMockito.when(对象.方法(Mockito.argThat(new ArgumentMatcher<String>(){
    // @Override public boolean matches(Object 参数)
    // { String 参数 = (String)参数;
    // if(参数逻辑判断) return true;
    // else 抛异常; }}))).thenReturn(返回值); -- return true 才会返回值
    // 或者用下面的
    // PowerMockito.when(对象.方法(Mockito.anyString())).then(new Answer<String>(){
    //   @Override



}
