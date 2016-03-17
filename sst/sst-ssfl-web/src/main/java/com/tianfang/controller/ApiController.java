package com.tianfang.controller;

import com.tianfang.business.dto.AddressesDto;
import com.tianfang.business.service.IAddressesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 接口数据api
 *
 * @Author wangxiang
 * @Date 2016/3/17 11:41
 */
@Controller
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    private IAddressesService addressesService;

    @RequestMapping(value = "addr")
    @ResponseBody
    public JsonData address(){
        AddressesDto dto = new AddressesDto();
        List<AddressesDto> addressList = addressesService.findAddressList(dto);
        if (null != addressList && addressList.size() > 0){
            Map<Integer, AddressesDto> pMap = new HashMap<Integer, AddressesDto>();//省
            Map<String, List<AddressesDto>> cMap = new HashMap<String,  List<AddressesDto>>();//市
            Map<String,  List<AddressesDto>> aMap = new HashMap<String,  List<AddressesDto>>();//区
            for (AddressesDto addr : addressList){
                if (addr.getLevel() == 1){
                    pMap.put(addr.getId(), addr);
                }
                if (addr.getLevel() == 2){
                    childArea(cMap, addr);
                }
                if (addr.getLevel() == 3){
                    childArea(aMap, addr);
                }
            }

            List<JsonP> ps = new ArrayList<JsonP>(pMap.size());
            Iterator<Integer> iterator = pMap.keySet().iterator();
            JsonP p;
            AddressesDto temp;
            while (iterator.hasNext()){
                temp = pMap.get(iterator.next());
                p = new JsonP();
                p.getP().setId(temp.getId());
                p.getP().setName(temp.getName());
                childC(p, cMap, aMap);

                ps.add(p);
            }

            JsonData data = new JsonData(ps);
            return data;
        }

        return null;
    }

    /**
     * 根据逻辑封装数据到市
     * @Author wangxiang
     * @Date 2016/3/17 14:20
     */
    private void childC(JsonP p, Map<String, List<AddressesDto>> cMap, Map<String, List<AddressesDto>> aMap) {
        List<AddressesDto> addrs = cMap.get(p.getP().getId()+"");
        if (null != addrs && addrs.size() > 0){
            List<JsonC> citys = new ArrayList<JsonC>(addrs.size());
            JsonC c;
            for (AddressesDto addr : addrs){
                c = new JsonC();
                c.getN().setId(addr.getId());
                c.getN().setName(addr.getName());
                childA(c, aMap);
                citys.add(c);
            }
            p.setC(citys);
        }
    }

    /**
     * 根据逻辑封装数据到地区
     * @Author wangxiang
     * @Date 2016/3/17 14:19
     */
    private void childA(JsonC c, Map<String, List<AddressesDto>> aMap) {
        List<AddressesDto> addrs = aMap.get(c.getN().getId()+"");
        if (null != addrs && addrs.size() > 0){
            List<JsonA> areas = new ArrayList<JsonA>(addrs.size());
            JsonA a;
            for (AddressesDto addr : addrs){
                a = new JsonA();
                a.getS().setId(addr.getId());
                a.getS().setName(addr.getName());

                areas.add(a);
            }
            c.setA(areas);
        }
    }

    /**
     * 根据parentId封装数据到相应的map集合中
     * @Author wangxiang
     * @Date 2016/3/17 14:18
     */
    private void childArea(Map<String, List<AddressesDto>> map,AddressesDto addr) {
        String key = addr.getParentId();
        if (map.containsKey(key)){
            map.get(key).add(addr);
        }else{
            List<AddressesDto> addrs = new ArrayList<AddressesDto>();
            addrs.add(addr);
            map.put(key, addrs);
        }
    }
}

/**
 * Json 返回数据封装
 */
class JsonData{
    private List<JsonP> citylist;

    public JsonData(List<JsonP> citylist) {
        this.citylist = citylist;
    }

    public List<JsonP> getCitylist() {
        return citylist;
    }

    public void setCitylist(List<JsonP> citylist) {
        this.citylist = citylist;
    }
}

/**
 * Json 省数据封装
 */
class JsonP{
    private BaseData p = new BaseData();
    private List<JsonC> c;

    public BaseData getP() {
        return p;
    }

    public void setP(BaseData p) {
        this.p = p;
    }

    public List<JsonC> getC() {
        return c;
    }

    public void setC(List<JsonC> c) {
        this.c = c;
    }
}

/**
 * Json 市数据封装
 */
class JsonC{
    private BaseData n = new BaseData();
    private List<JsonA> a;

    public BaseData getN() {
        return n;
    }

    public void setN(BaseData n) {
        this.n = n;
    }

    public List<JsonA> getA() {
        return a;
    }

    public void setA(List<JsonA> a) {
        this.a = a;
    }
}

/**
 * Json 区数据封装
 */
class JsonA{
    private BaseData s = new BaseData();

    public BaseData getS() {
        return s;
    }

    public void setS(BaseData s) {
        this.s = s;
    }
}

/**
 * Json 省/市/区 基本数据封装
 */
class BaseData{
    private Integer id;     // 地区id
    private String name;    // 地区名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}