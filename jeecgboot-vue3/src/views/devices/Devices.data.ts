import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '创建日期',
    align: "center",
    dataIndex: 'createTime'
  },
  {
    title: '更新日期',
    align: "center",
    dataIndex: 'updateTime'
  },
  {
    title: 'device id',
    align: "center",
    dataIndex: 'deviceId'
  },
  {
    title: 'device type ',
    align: "center",
    dataIndex: 'deviceType'
  },
  {
    title: 'wechat open id',
    align: "center",
    dataIndex: 'openId'
  },
  {
    title: '是否启用',
    align: "center",
    dataIndex: 'en',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'1'},{text:'否',value:'2'}]);
     },
  },
];

// 高级查询数据
export const superQuerySchema = {
  createTime: {title: '创建日期',order: 0,view: 'datetime', type: 'string',},
  updateTime: {title: '更新日期',order: 1,view: 'datetime', type: 'string',},
  deviceId: {title: 'device id',order: 2,view: 'text', type: 'string',},
  deviceType: {title: 'device type ',order: 3,view: 'text', type: 'string',},
  openId: {title: 'wechat open id',order: 4,view: 'text', type: 'string',},
  en: {title: '是否启用',order: 5,view: 'number', type: 'number',},
};
