package com.zhenlianhui.netty.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zhenlianhui.netty.bean.ModbusData;

public interface ModbusDataRepository extends JpaRepository<ModbusData, String>, JpaSpecificationExecutor<ModbusData> {

}
