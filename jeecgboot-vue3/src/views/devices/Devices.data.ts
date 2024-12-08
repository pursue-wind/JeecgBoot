import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
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
];

// 高级查询数据
export const superQuerySchema = {
  deviceId: {title: 'device id',order: 0,view: 'text', type: 'string',},
  deviceType: {title: 'device type ',order: 1,view: 'text', type: 'string',},
  openId: {title: 'wechat open id',order: 2,view: 'text', type: 'string',},
};
