<#assign mixins = []>
<#list w.getWorkspace().getModElements() as element>
  <#assign providedmixins = false>
  <#if element.getGeneratableElement().mixins??>
    <#assign providedmixins = element.getGeneratableElement().mixins>
  </#if>
  <#if providedmixins != false>
    <#list providedmixins as mixin>
       <#if !mixins?seq_contains(mixin)>
         <#assign mixins += [mixin]>
       </#if>
    </#list>
  </#if>
</#list>
{
  "required": true,
  "package": "${package}.mixins",
  "compatibilityLevel": "JAVA_17",
  "refmap": "${modid}.refmap.json",
  "mixins": [
    <#if mixins?has_content>
      <#list mixins as mixin>
        "${mixin}"<#sep>,
      </#list>
    </#if>
  ],
  "client": [],
  "minVersion": "0.8"
}