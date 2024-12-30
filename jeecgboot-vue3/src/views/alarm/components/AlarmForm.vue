<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="AlarmForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="device_id" v-bind="validateInfos.deviceId" id="AlarmForm-deviceId" name="deviceId">
								<a-input v-model:value="formData.deviceId" placeholder="请输入device_id"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="提醒时间" v-bind="validateInfos.alarmTime" id="AlarmForm-alarmTime" name="alarmTime">
								<a-input-number v-model:value="formData.alarmTime" placeholder="请输入提醒时间" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="周一到周日分别对应 2 4 8 16 32 64 128" v-bind="validateInfos.alarmDate" id="AlarmForm-alarmDate" name="alarmDate">
								<a-input-number v-model:value="formData.alarmDate" placeholder="请输入周一到周日分别对应 2 4 8 16 32 64 128" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="关联的类型" v-bind="validateInfos.relaType" id="AlarmForm-relaType" name="relaType">
								<j-dict-select-tag type='radio' v-model:value="formData.relaType" dictCode="alarm_type" placeholder="请选择关联的类型"  allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="关联类型对应的id" v-bind="validateInfos.relaId" id="AlarmForm-relaId" name="relaId">
								<a-input v-model:value="formData.relaId" placeholder="请输入关联类型对应的id"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="提醒对应的标题文本" v-bind="validateInfos.title" id="AlarmForm-title" name="title">
								<a-input v-model:value="formData.title" placeholder="请输入提醒对应的标题文本"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="描述" v-bind="validateInfos.descTxt" id="AlarmForm-descTxt" name="descTxt">
								<a-textarea v-model:value="formData.descTxt" :rows="4" placeholder="请输入描述" />
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="是否重复" v-bind="validateInfos.isRepeat" id="AlarmForm-isRepeat" name="isRepeat">
								<j-switch v-model:value="formData.isRepeat" :options="[1,2]" ></j-switch>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="是否启用" v-bind="validateInfos.isOpen" id="AlarmForm-isOpen" name="isOpen">
								<j-switch v-model:value="formData.isOpen" :options="[1,2]" ></j-switch>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="是否完成" v-bind="validateInfos.isFinish" id="AlarmForm-isFinish" name="isFinish">
								<j-switch v-model:value="formData.isFinish" :options="[1,2]" ></j-switch>
							</a-form-item>
						</a-col>
          </a-row>
        </a-form>
      </template>
    </JFormContainer>
  </a-spin>
</template>

<script lang="ts" setup>
  import { ref, reactive, defineExpose, nextTick, defineProps, computed, onMounted } from 'vue';
  import { defHttp } from '/@/utils/http/axios';
  import { useMessage } from '/@/hooks/web/useMessage';
  import JDictSelectTag from '/@/components/Form/src/jeecg/components/JDictSelectTag.vue';
  import JSwitch from '/@/components/Form/src/jeecg/components/JSwitch.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../Alarm.api';
  import { Form } from 'ant-design-vue';
  import JFormContainer from '/@/components/Form/src/container/JFormContainer.vue';
  const props = defineProps({
    formDisabled: { type: Boolean, default: false },
    formData: { type: Object, default: () => ({})},
    formBpm: { type: Boolean, default: true }
  });
  const formRef = ref();
  const useForm = Form.useForm;
  const emit = defineEmits(['register', 'ok']);
  const formData = reactive<Record<string, any>>({
    id: '',
    deviceId: '',   
    alarmTime: undefined,
    alarmDate: undefined,
    relaType: undefined,
    relaId: '',   
    title: '',   
    descTxt: '',   
    isRepeat: undefined,
    isOpen: undefined,
    isFinish: undefined,
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
    deviceId: [{ required: true, message: '请输入device_id!'},],
    relaType: [{ required: true, message: '请输入关联的类型!'},],
  });
  const { resetFields, validate, validateInfos } = useForm(formData, validatorRules, { immediate: false });

  // 表单禁用
  const disabled = computed(()=>{
    if(props.formBpm === true){
      if(props.formData.disabled === false){
        return false;
      }else{
        return true;
      }
    }
    return props.formDisabled;
  });

  
  /**
   * 新增
   */
  function add() {
    edit({});
  }

  /**
   * 编辑
   */
  function edit(record) {
    nextTick(() => {
      resetFields();
      const tmpData = {};
      Object.keys(formData).forEach((key) => {
        if(record.hasOwnProperty(key)){
          tmpData[key] = record[key]
        }
      })
      //赋值
      Object.assign(formData, tmpData);
    });
  }

  /**
   * 提交数据
   */
  async function submitForm() {
    try {
      // 触发表单验证
      await validate();
    } catch ({ errorFields }) {
      if (errorFields) {
        const firstField = errorFields[0];
        if (firstField) {
          formRef.value.scrollToField(firstField.name, { behavior: 'smooth', block: 'center' });
        }
      }
      return Promise.reject(errorFields);
    }
    confirmLoading.value = true;
    const isUpdate = ref<boolean>(false);
    //时间格式化
    let model = formData;
    if (model.id) {
      isUpdate.value = true;
    }
    //循环数据
    for (let data in model) {
      //如果该数据是数组并且是字符串类型
      if (model[data] instanceof Array) {
        let valueType = getValueType(formRef.value.getProps, data);
        //如果是字符串类型的需要变成以逗号分割的字符串
        if (valueType === 'string') {
          model[data] = model[data].join(',');
        }
      }
    }
    await saveOrUpdate(model, isUpdate.value)
      .then((res) => {
        if (res.success) {
          createMessage.success(res.message);
          emit('ok');
        } else {
          createMessage.warning(res.message);
        }
      })
      .finally(() => {
        confirmLoading.value = false;
      });
  }


  defineExpose({
    add,
    edit,
    submitForm,
  });
</script>

<style lang="less" scoped>
  .antd-modal-form {
    padding: 14px;
  }
</style>
