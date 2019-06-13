package com.springboot.seckill.repository;

import com.springboot.seckill.common.entity.Seckill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeckillRepository extends JpaRepository<Seckill, Long> {
	
	
}
