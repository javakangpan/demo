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



}
