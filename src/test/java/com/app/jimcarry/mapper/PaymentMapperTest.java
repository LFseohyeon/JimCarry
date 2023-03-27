package com.app.jimcarry.mapper;

import com.app.jimcarry.domain.vo.PaymentVO;
import com.app.jimcarry.domain.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@Transactional
public class PaymentMapperTest {
    @Autowired
    PaymentMapper paymentMapper;
    @Autowired
    PaymentVO paymentVO;
}
