
function  syncMerInfo() {
    if(document.getElementById("syncInfo").checked){
        $("#contactName").val($("#legalPersonName").val()).change;
        $("#contactPhone").val($("#legalPersonPhone").val()).change;
        $("#contactMail").val($("#legalPersonMail").val()).change;
        $("#contactIdType").val($("#legalPersonIdType").val()).change;
        $("#contactCertId").val($("#legalPersonId").val()).change;
        $("#contactAddress").val($("#legalPersonAddress").val()).change;
    }
}


