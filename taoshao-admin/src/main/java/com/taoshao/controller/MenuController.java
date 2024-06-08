package com.taoshao.controller;

import com.taoshao.domain.ResponseResult;
import com.taoshao.domain.dto.MenuListDto;
import com.taoshao.domain.entity.Menu;
import com.taoshao.domain.vo.MenuListVo;
import com.taoshao.domain.vo.MenuTreeVo;
import com.taoshao.domain.vo.MenuVo;
import com.taoshao.domain.vo.RoleMenuTreeVo;
import com.taoshao.service.MenuService;
import com.taoshao.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author taoshao
 * @Date 2024/6/7
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public ResponseResult<List<MenuListVo>> list(MenuListDto menuListDto) {
        List<MenuListVo> menuListVoList = menuService.menuList(menuListDto);
        return ResponseResult.okResult(menuListVoList);
    }

    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        return menuService.add(menu);
    }

    @GetMapping("/{id}")
    public ResponseResult<MenuVo> getMenuById(@PathVariable Long id) {
        MenuVo menuVo = menuService.getMenuById(id);
        return ResponseResult.okResult(menuVo);
    }

    @PutMapping
    public ResponseResult update(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    @DeleteMapping({"/{id}"})
    public ResponseResult delete(@PathVariable Long id){
        return menuService.delete(id);
    }

    @GetMapping("/treeselect")
    public ResponseResult<List<MenuTreeVo>> treeSelect(){
        List<MenuTreeVo> menuTreeVos = menuService.treeSelect();
       return ResponseResult.okResult(menuTreeVos);
    }

    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult<RoleMenuTreeVo> roleMenuTreeSelectById(@PathVariable Long id){
        RoleMenuTreeVo roleMenuTreeVos = menuService.roleMenuTreeSelectById(id);
        return ResponseResult.okResult(roleMenuTreeVos);
    }


}
