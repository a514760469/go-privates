package ai.lifo.data.web;

import ai.lifo.data.entity.SysUser;
import ai.lifo.data.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglifeng
 * @since 2024-08-28
 */
@RestController
@RequiredArgsConstructor
public class SysController {

    private final SysUserMapper sysUserMapper;

    @GetMapping("/sysuser/{id}")
    public SysUser getSysUser(@PathVariable String id) {
        return sysUserMapper.selectById(id);
    }

}
