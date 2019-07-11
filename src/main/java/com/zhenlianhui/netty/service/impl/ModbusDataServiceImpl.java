package com.zhenlianhui.netty.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhenlianhui.netty.bean.ModbusData;
import com.zhenlianhui.netty.dao.ModbusDataRepository;
import com.zhenlianhui.netty.service.ModbusDataService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ModbusDataServiceImpl implements ModbusDataService {

	@Autowired
	private ModbusDataRepository modbusDataRepository;

	@Override
	public void createModbusData() {
		// 电压
		ModbusData modbusData0 = new ModbusData();
		modbusData0.setId(UUID.randomUUID().toString().replace("-", ""));
		modbusData0.setTerminal("3_0");
		modbusData0.setDeviceId("000000800013");
		modbusData0.setValue(RandomUtils.nextInt(9200, 9300));
		modbusData0.setCreateTime(new Date());
		modbusData0.setInsertTime(new Date());
		// 电流
		ModbusData modbusData1 = new ModbusData();
		modbusData1.setId(UUID.randomUUID().toString().replace("-", ""));
		modbusData1.setTerminal("3_1");
		modbusData1.setDeviceId("000000800013");
		modbusData1.setValue(RandomUtils.nextInt(120, 150));
		modbusData1.setCreateTime(new Date());
		modbusData1.setInsertTime(new Date());
		//
		modbusDataRepository.save(modbusData0);
		modbusDataRepository.save(modbusData1);
		log.info("添加电压数据:" + modbusData0.getValue() + ", 电流数据:" + modbusData1.getValue());
	}

}
