import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: 'device_id',
    align: "center",
    dataIndex: 'deviceId'
  },
  {
    title: '关联的类型',
    align: "center",
    dataIndex: 'favType_dictText'
  },
  {
    title: '标题',
    align: "center",
    dataIndex: 'title'
  },
  {
    title: '描述',
    align: "center",
    dataIndex: 'txt'
  },
  {
    title: '是否启用',
    align: "center",
    dataIndex: 'isOpen',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'1'},{text:'否',value:'2'}]);
     },
  },
];

// 高级查询数据
export const superQuerySchema = {
  deviceId: {title: 'device_id',order: 0,view: 'text', type: 'string',},
  favType: {title: '关联的类型',order: 1,view: 'number', type: 'number',dictCode: 'fav_type',},
  title: {title: '标题',order: 2,view: 'text', type: 'string',},
  txt: {title: '描述',order: 3,view: 'textarea', type: 'string',},
  isOpen: {title: '是否启用',order: 4,view: 'number', type: 'number',},
};
