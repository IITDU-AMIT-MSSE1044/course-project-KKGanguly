<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:src="http://www.srcML.org/srcML/src"
>
<xsl:template match="text()" />
<xsl:template match="//src:function">	
	<xsl:message>Function <xsl:value-of select="src:name[1]"/> Class <xsl:value-of select="/src:unit//src:class/src:name"/></xsl:message>
	<xsl:for-each select="current()//src:call">
		<xsl:choose>
            <xsl:when test="parent::node()/src:operator and  parent::node()/src:operator='new'">        
            </xsl:when>
            <xsl:otherwise>
            <xsl:if test="src:name/src:name[1]">
				<xsl:variable name="varname" select="src:name/src:name[1]"/>
				<xsl:if test="src:name/src:name[last()]!='' and /src:unit//src:decl[src:name[1]=$varname]/src:type/src:name!=''">
					<xsl:message><xsl:value-of select="src:name/src:name[last()]"/>,<xsl:value-of select="/src:unit//src:decl[src:name[1]=$varname]/src:type/src:name"/></xsl:message>
				</xsl:if >
				<xsl:if test="not(/src:unit//src:decl[src:name[1]=$varname])">
					<xsl:message><xsl:value-of select="src:name/src:name[last()]"/>,<xsl:value-of select="src:name/src:name[1]"/></xsl:message>
				</xsl:if >
			</xsl:if>
			<xsl:if test="not(src:name/src:name[1])">
				<xsl:if test="src:name!=''">
					<xsl:message><xsl:value-of select="src:name"/>,<xsl:value-of select="/src:unit//src:class/src:name"/></xsl:message>
				</xsl:if >
			</xsl:if>  
		</xsl:otherwise>
        </xsl:choose>
    </xsl:for-each>
</xsl:template>
</xsl:stylesheet>
