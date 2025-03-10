<template>
  <a-spin :spinning="confirmLoading">
    <JFormContainer :disabled="disabled">
      <template #detail>
        <a-form ref="formRef" class="antd-modal-form" :labelCol="labelCol" :wrapperCol="wrapperCol" name="ResourcesForm">
          <a-row>
						<a-col :span="24">
							<a-form-item label="分类ID" v-bind="validateInfos.cid" id="ResourcesForm-cid" name="cid">
								<j-tree-select
									dict="sys_category,name,id"
									pidValue="0"
									
									v-model:value="formData.cid"
									@change="(value) => handleFormChange('cid', value)" allow-clear >
								</j-tree-select>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="资源名" v-bind="validateInfos.name" id="ResourcesForm-name" name="name">
								<a-input v-model:value="formData.name" placeholder="请输入资源名"  allow-clear ></a-input>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="img url" v-bind="validateInfos.img" id="ResourcesForm-img" name="img">
								<j-image-upload :fileMax="0" v-model:value="formData.img" ></j-image-upload>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="gif url" v-bind="validateInfos.gif" id="ResourcesForm-gif" name="gif">
								<j-image-upload :fileMax="0" v-model:value="formData.gif" ></j-image-upload>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="mp3 url" v-bind="validateInfos.au" id="ResourcesForm-au" name="au">
								<j-upload v-model:value="formData.au"   ></j-upload>
							</a-form-item>
						</a-col>
						<a-col :span="24">
							<a-form-item label="资源详情" v-bind="validateInfos.txt" id="ResourcesForm-txt" name="txt">
								<a-input v-model:value="formData.txt" placeholder="请输入资源详情"  allow-clear ></a-input>
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
  import JTreeSelect from '/@/components/Form/src/jeecg/components/JTreeSelect.vue';
  import JUpload from '/@/components/Form/src/jeecg/components/JUpload/JUpload.vue';
  import JImageUpload from '/@/components/Form/src/jeecg/components/JImageUpload.vue';
  import { getValueType } from '/@/utils';
  import { saveOrUpdate } from '../Resources.api';
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
    cid: '',   
    name: '',   
    img: '',   
    gif: '',   
    au: '',   
    txt: '',   
  });
  const { createMessage } = useMessage();
  const labelCol = ref<any>({ xs: { span: 24 }, sm: { span: 5 } });
  const wrapperCol = ref<any>({ xs: { span: 24 }, sm: { span: 16 } });
  const confirmLoading = ref<boolean>(false);
  //表单验证
  const validatorRules = reactive({
    cid: [{ required: true, message: '请输入分类ID!'},],
    name: [{ required: true, message: '请输入资源名!'},],
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


  /**
   * 值改变事件触发
   * @param key
   * @param value
   */
  function handleFormChange(key, value) {
    formData[key] = value;
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
