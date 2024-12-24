import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '客户名',
    align: "center",
    dataIndex: 'cus'
  },
  {
    title: 'IOT aksk配置',
    align: "center",
    dataIndex: 'iot'
  },
  {
    title: '微信aksk配置',
    align: "center",
    dataIndex: 'wechat'
  },
];

// 高级查询数据
export const superQuerySchema = {
  cus: {title: '客户名',order: 0,view: 'text', type: 'string',},
  iot: {title: 'IOT aksk配置',order: 1,view: 'textarea', type: 'string',},
  wechat: {title: '微信aksk配置',order: 2,view: 'textarea', type: 'string',},
};
