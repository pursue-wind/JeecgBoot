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
    dataIndex: 'deviceId_dictText'
  },
  {
    title: 'day_of_week',
    align: "center",
    dataIndex: 'dow'
  },
  {
    title: 'order',
    align: "center",
    dataIndex: 'ord'
  },
  {
    title: 'class name',
    align: "center",
    dataIndex: 'clName'
  },
];

// 高级查询数据
export const superQuerySchema = {
  createTime: {title: '创建日期',order: 0,view: 'datetime', type: 'string',},
  updateTime: {title: '更新日期',order: 1,view: 'datetime', type: 'string',},
  deviceId: {title: 'device id',order: 2,view: 'sel_search', type: 'string',dictTable: "devices", dictCode: 'device_id', dictText: 'device_id',},
  dow: {title: 'day_of_week',order: 3,view: 'number', type: 'number',},
  ord: {title: 'order',order: 4,view: 'number', type: 'number',},
  clName: {title: 'class name',order: 5,view: 'text', type: 'string',},
};
