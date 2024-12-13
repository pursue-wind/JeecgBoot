import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '分类ID',
    align: "center",
    dataIndex: 'cid_dictText'
  },
  {
    title: '资源名',
    align: "center",
    dataIndex: 'name'
  },
  {
    title: 'img url',
    align: "center",
    dataIndex: 'img',
    customRender: render.renderImage,
  },
  {
    title: 'gif url',
    align: "center",
    dataIndex: 'gif',
    customRender: render.renderImage,
  },
  {
    title: 'mp3 url',
    align: "center",
    dataIndex: 'au',
  },
  {
    title: '资源详情',
    align: "center",
    dataIndex: 'txt'
  },
];

// 高级查询数据
export const superQuerySchema = {
  cid: {title: '分类ID',order: 0,view: 'sel_tree', type: 'string',dict: 'sys_category,name,id', pidValue: '0',},
  name: {title: '资源名',order: 1,view: 'text', type: 'string',},
  img: {title: 'img url',order: 2,view: 'image', type: 'string',},
  gif: {title: 'gif url',order: 3,view: 'image', type: 'string',},
  au: {title: 'mp3 url',order: 4,view: 'file', type: 'string',},
  txt: {title: '资源详情',order: 5,view: 'text', type: 'string',},
};
