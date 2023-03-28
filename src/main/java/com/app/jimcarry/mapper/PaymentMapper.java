package com.app.jimcarry.mapper;

import com.app.jimcarry.domain.dto.PageDTO;
import com.app.jimcarry.domain.dto.PaymentDTO;
import com.app.jimcarry.domain.dto.SearchDTO;
import com.app.jimcarry.domain.vo.PaymentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PaymentMapper {
    //    추가
    public void insert(PaymentVO paymentVO);

    //    조회
    public PaymentVO select(Long payId);

    //    전체조회
    public List<PaymentDTO> selectAllPayment(@Param("page") PageDTO PageDTO);

    //    수정
    public void update(PaymentVO paymentVO);

    //    삭제
    public void delete(Long payId);

    // 조건조회
    public List<PaymentVO> selectAllBy(@Param("page") PageDTO pageDTO);

    // 조건조회 개수
    public int totalBy(@Param("page") SearchDTO searchDTO);

    // 총 개수 조회
    public int total();
}
