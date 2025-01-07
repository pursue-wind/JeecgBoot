import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'http addr',
    align: "center",
    dataIndex: 'addr'
  },
  {
    title: 'key',
    align: "center",
    dataIndex: 'ke'
  },
];

// 高级查询数据
export const superQuerySchema = {
  addr: {title: 'http addr',order: 0,view: 'text', type: 'string',},
  ke: {title: 'key',order: 1,view: 'text', type: 'string',},
};
