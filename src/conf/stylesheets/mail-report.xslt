<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="text"/>

<xsl:template match="/">
<xsl:apply-templates select="/competition/classes/class/registration">
    <xsl:sort select="*[name()='mail']" data-type="text" order="ascending"/>
</xsl:apply-templates>
</xsl:template>

<xsl:template match="registration">
    <xsl:if test="mail and string-length(normalize-space(mail)) > 0">
        <xsl:value-of select="mail"/>
        <xsl:text>
</xsl:text>
    </xsl:if>
</xsl:template>

</xsl:stylesheet>
