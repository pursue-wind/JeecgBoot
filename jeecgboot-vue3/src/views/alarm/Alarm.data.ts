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
    title: '提醒时间',
    align: "center",
    dataIndex: 'alarmTime'
  },
  {
    title: '周一到周日分别对应 2 4 8 16 32 64 128',
    align: "center",
    dataIndex: 'alarmDate'
  },
  {
    title: '关联的类型',
    align: "center",
    dataIndex: 'relaType_dictText'
  },
  {
    title: '关联类型对应的id',
    align: "center",
    dataIndex: 'relaId'
  },
  {
    title: '提醒对应的标题文本',
    align: "center",
    dataIndex: 'title'
  },
  {
    title: '描述',
    align: "center",
    dataIndex: 'descTxt'
  },
  {
    title: '是否重复',
    align: "center",
    dataIndex: 'isRepeat',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'1'},{text:'否',value:'2'}]);
     },
  },
  {
    title: '是否启用',
    align: "center",
    dataIndex: 'isOpen',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'1'},{text:'否',value:'2'}]);
     },
  },
  {
    title: '是否完成',
    align: "center",
    dataIndex: 'isFinish',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'1'},{text:'否',value:'2'}]);
     },
  },
];

// 高级查询数据
export const superQuerySchema = {
  deviceId: {title: 'device_id',order: 0,view: 'text', type: 'string',},
  alarmTime: {title: '提醒时间',order: 1,view: 'text', type: 'string',},
  alarmDate: {title: '周一到周日分别对应 2 4 8 16 32 64 128',order: 2,view: 'number', type: 'number',},
  relaType: {title: '关联的类型',order: 3,view: 'number', type: 'number',dictCode: 'alarm_type',},
  relaId: {title: '关联类型对应的id',order: 4,view: 'text', type: 'string',},
  title: {title: '提醒对应的标题文本',order: 5,view: 'text', type: 'string',},
  descTxt: {title: '描述',order: 6,view: 'textarea', type: 'string',},
  isRepeat: {title: '是否重复',order: 7,view: 'number', type: 'number',},
  isOpen: {title: '是否启用',order: 8,view: 'number', type: 'number',},
  isFinish: {title: '是否完成',order: 9,view: 'number', type: 'number',},
};
