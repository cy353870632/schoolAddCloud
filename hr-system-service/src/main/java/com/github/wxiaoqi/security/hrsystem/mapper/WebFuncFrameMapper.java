package com.github.wxiaoqi.security.hrsystem.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.github.wxiaoqi.security.hrsystem.entity.User;
import com.github.wxiaoqi.security.hrsystem.entity.WebFuncFrame;
import com.github.wxiaoqi.security.hrsystem.vo.UserInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebFuncFrameMapper extends BaseMapper<WebFuncFrame> {

    public List<WebFuncFrame> selectByfunccode(@Param("funccode") List<String> funccodelist);

    public List<WebFuncFrame> selectByfunccodeAndmodid(@Param("funccode")List<String> funccode,@Param("modeid")String modeid);

    public List<WebFuncFrame> callWebGetFuncView(@Param("mid")String modid,@Param("A0188")int A0188);

}