/**
 * @Package tk.yimiao.yimiaocloud.microservice.mall.manager.controller
 * @Description: TODO
 * @author yimiao
 * @date 2019-03-12 21:04
 * @version V1.0
 */
package tk.yimiao.yimiaocloud.microservice.mall.manager.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.yimiao.yimiaocloud.common.model.DataTablesResult;
import tk.yimiao.yimiaocloud.common.model.Result;
import tk.yimiao.yimiaocloud.common.util.ResultUtil;
import tk.yimiao.yimiaocloud.microservice.mall.base.dto.MemberDto;
import tk.yimiao.yimiaocloud.microservice.mall.base.pojo.TbMember;
import tk.yimiao.yimiaocloud.microservice.mall.base.service.MemberService;

@Slf4j
@RestController
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*", methods = {RequestMethod.GET,
        RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
        RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins = "*")
@Api(description = "会员管理")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/member/list", method = RequestMethod.GET)
    @ApiOperation(value = "分页多条件搜索获取会员列表")
    public DataTablesResult getMemberList(int draw, int start, int length, String searchKey,
                                          String minDate, String maxDate,
                                          @RequestParam("search[value]") String search,
                                          @RequestParam("order[0][column]") int orderCol,
                                          @RequestParam("order[0][dir]") String orderDir) {

        //获取客户端需要排序的列
        String[] cols = {"checkbox", "id", "username", "sex", "phone", "email", "address", "created", "updated", "state"};
        String orderColumn = cols[orderCol];
        //默认排序列
        if (orderColumn == null) {
            orderColumn = "created";
        }
        //获取排序方式 默认为desc(asc)
        if (orderDir == null) {
            orderDir = "desc";
        }
        if (!search.isEmpty()) {
            searchKey = search;
        }
        DataTablesResult result = memberService.getMemberList(draw, start, length, searchKey, minDate, maxDate, orderColumn, orderDir);
        return result;
    }

    @RequestMapping(value = "/member/list/remove", method = RequestMethod.GET)
    @ApiOperation(value = "分页多条件搜索已删除会员列表")
    public DataTablesResult getDelMemberList(int draw, int start, int length, String searchKey,
                                             String minDate, String maxDate,
                                             @RequestParam("search[value]") String search,
                                             @RequestParam("order[0][column]") int orderCol,
                                             @RequestParam("order[0][dir]") String orderDir) {

        //获取客户端需要排序的列
        String[] cols = {"checkbox", "id", "username", "sex", "phone", "email", "address", "created", "updated", "state"};
        String orderColumn = cols[orderCol];
        //默认排序列
        if (orderColumn == null) {
            orderColumn = "created";
        }
        //获取排序方式 默认为desc(asc)
        if (orderDir == null) {
            orderDir = "desc";
        }
        if (!search.isEmpty()) {
            searchKey = search;
        }
        DataTablesResult result = memberService.getRemoveMemberList(draw, start, length, searchKey, minDate, maxDate, orderColumn, orderDir);
        return result;
    }

    @RequestMapping(value = "/member/count", method = RequestMethod.GET)
    @ApiOperation(value = "获得总会员数目")
    public DataTablesResult getMemberCount() {

        return memberService.getMemberCount();
    }

    @RequestMapping(value = "/member/count/remove", method = RequestMethod.GET)
    @ApiOperation(value = "获得移除总会员数目")
    public DataTablesResult getRemoveMemberCount() {

        return memberService.getRemoveMemberCount();
    }

    @RequestMapping(value = "/member/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加会员")
    public Result<TbMember> createMember(MemberDto memberDto) {

        TbMember newTbMember = memberService.addMember(memberDto);
        return new ResultUtil<TbMember>().setData(newTbMember);
    }

    @RequestMapping(value = "/member/stop/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "停用会员")
    public Result<TbMember> stopMember(@PathVariable Long id) {

        TbMember tbMember = memberService.alertMemberState(id, 0);
        return new ResultUtil<TbMember>().setData(tbMember);
    }

    @RequestMapping(value = "/member/start/{ids}", method = RequestMethod.PUT)
    @ApiOperation(value = "启用会员")
    public Result<TbMember> startMember(@PathVariable Long[] ids) {

        for (Long id : ids) {
            memberService.alertMemberState(id, 1);
        }
        return new ResultUtil<TbMember>().setData(null);
    }

    @RequestMapping(value = "/member/remove/{ids}", method = RequestMethod.PUT)
    @ApiOperation(value = "移除会员")
    public Result<TbMember> removeMember(@PathVariable Long[] ids) {

        for (Long id : ids) {
            memberService.alertMemberState(id, 2);
        }
        return new ResultUtil<TbMember>().setData(null);
    }

    @RequestMapping(value = "/member/del/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "彻底删除会员")
    public Result<TbMember> deleteMember(@PathVariable Long[] ids) {

        for (Long id : ids) {
            memberService.deleteMember(id);
        }
        return new ResultUtil<TbMember>().setData(null);
    }

    @RequestMapping(value = "/member/changePass/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改会员密码")
    public Result<TbMember> changeMemberPassword(@PathVariable Long id, MemberDto memberDto) {

        TbMember tbMember = memberService.changePassMember(id, memberDto);
        return new ResultUtil<TbMember>().setData(tbMember);
    }

    @RequestMapping(value = "/member/update/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改会员信息")
    public Result<TbMember> updateMember(@PathVariable Long id, MemberDto memberDto) {

        TbMember tbMember = memberService.updateMember(id, memberDto);
        return new ResultUtil<TbMember>().setData(tbMember);
    }

    @RequestMapping(value = "/member/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "通过ID获取会员信息")
    public Result<TbMember> getMemberById(@PathVariable Long id) {

        TbMember tbMember = memberService.getMemberById(id);
        return new ResultUtil<TbMember>().setData(tbMember);
    }

    @RequestMapping(value = "/member/username", method = RequestMethod.GET)
    @ApiOperation(value = "验证注册名是否存在")
    public Boolean validateUsername(String username) {

        return memberService.getMemberByUsername(username) == null;
    }

    @RequestMapping(value = "/member/phone", method = RequestMethod.GET)
    @ApiOperation(value = "验证注册手机是否存在")
    public Boolean validatePhone(String phone) {

        return memberService.getMemberByPhone(phone) == null;
    }

    @RequestMapping(value = "/member/email", method = RequestMethod.GET)
    @ApiOperation(value = "验证注册邮箱是否存在")
    public Boolean validateEmail(String email) {

        return memberService.getMemberByEmail(email) == null;
    }

    @RequestMapping(value = "/member/edit/{id}/username", method = RequestMethod.GET)
    @ApiOperation(value = "验证编辑用户名是否存在")
    public Boolean validateEditUsername(@PathVariable Long id, String username) {

        return memberService.getMemberByEditUsername(id, username) == null;
    }

    @RequestMapping(value = "/member/edit/{id}/phone", method = RequestMethod.GET)
    @ApiOperation(value = "验证编辑手机是否存在")
    public Boolean validateEditPhone(@PathVariable Long id, String phone) {

        return memberService.getMemberByEditPhone(id, phone) == null;
    }

    @RequestMapping(value = "/member/edit/{id}/email", method = RequestMethod.GET)
    @ApiOperation(value = "验证编辑邮箱是否存在")
    public Boolean validateEditEmail(@PathVariable Long id, String email) {

        return memberService.getMemberByEditEmail(id, email) == null;
    }
}
