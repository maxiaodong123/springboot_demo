package com.springboot.seckill.queue.redis;

import com.springboot.seckill.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消费者
 * @author 科帮网 By https://blog.52itstyle.com
 */
@Service
public class RedisConsumer {
	
	@Autowired
	private ISeckillService seckillService;
	
    public void receiveMessage(String message) {
        //收到通道的消息之后执行秒杀操作(超卖)
    	String[] array = message.split(";"); 
    	seckillService.startSeckil(Long.parseLong(array[0]), Long.parseLong(array[1]));
    }
}