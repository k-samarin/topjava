const commonMessages = ["common.deleted", "common.saved", "common.enabled", "common.disabled",
    "common.errorStatus", "common.confirm"];
commonMessages.forEach(function(key) {
    i18n[key] = "<spring:message code='" + key + "'/>";
});