package ai.lifo.data.web;

import ai.lifo.data.entity.SysUser;
import ai.lifo.data.mapper.MybatisOrderRepository;
import ai.lifo.data.mapper.SysUserMapper;
import ai.lifo.sharding.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhanglifeng
 * @since 2024-08-28
 */
@RestController
@RequiredArgsConstructor
public class SysController {

    private final SysUserMapper sysUserMapper;

    private final MybatisOrderRepository orderRepository;

    @GetMapping("/sysuser/{id}")
    public SysUser getSysUser(@PathVariable String id) {
        return sysUserMapper.selectById(id);
    }

    @PostMapping("/sysuser")
    public SysUser saveSysUser(@RequestBody SysUser sysUser) {
        sysUserMapper.insert(sysUser);
        return sysUser;
    }


    @PostMapping("/order")
    public Order saveOrder(@RequestBody Order order) {
        orderRepository.insert(order);
        return order;
    }


    @GetMapping("/order/{id}")
    public Order getOrder(@PathVariable String id) {
        return orderRepository.getOrder(Long.valueOf(id));
    }
}
