/**
 * Filename: ModbusData.java
 * Description: 
 * Company: 重庆臻链汇物联网科技有限公司
 * Copyright: Copyright ©2018
 * Created by CageGao in 2019年06月19日
 * @version 1.0
 */
package com.zhenlianhui.netty.bean;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Modbus设备上传数据实体类
 *
 * @author GaoJin
 * @date 2018-06-05 18:03
 */
@Entity
@Table(name = "modbus_data")
@EntityListeners(AuditingEntityListener.class)
@Data
public class ModbusData {

    @Id
    @Column(name = "id", length = 32)
    private String id;

    @Basic
    @Column(name = "device_id", length = 12, nullable = false)
    private String deviceId;

    @Basic
    @Column(name = "terminal", length = 10, nullable = false)
    private String terminal;

    @Basic
    @Column(name = "data_value")
    private Integer value;

    @Basic
    @Column(name = "create_time")
    private Date createTime;

    @Basic
    @Column(name = "insert_time")
    @CreatedDate // 添加数据时自动生成该时间字段的值
    private Date insertTime;
}
