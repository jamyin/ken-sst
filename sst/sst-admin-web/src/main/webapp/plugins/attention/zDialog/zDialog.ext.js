/**
 * 
 * zDialog扩展方法 Author: 刘博
 * 
 */
(function($) {
	/**
	 * getElementById:获取当前dialog的某一个元素对象 id:当前元素文本框的id Author: 刘博
	 */
	top.Dialog.prototype.getElementById = function(id) {
		return this.innerDoc.getElementById(id);
	}
	/**
	 * getDialogById:获取某一个dialog对象 id:dialog的id Author: 刘博
	 */
	top.Dialog.getDialogById = function(id) {
		return this.getInstance(id);
	}
	/**
	 * elementId:下拉框控件的id
	 * valueId:  属性值的id
	 * setSelectedValue:给下拉框元素 放入选择值;
	 * 
	 */
	top.Dialog.prototype.setSelectedValue = function(elementId,valueId) {
	 $(this.innerDoc).find(elementId+" option[value='"+valueId+"']").prop("selected","selected");
   }

})(jQuery);