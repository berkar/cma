<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
  xmlns:java="http://xml.apache.org/xslt/java"
  exclude-result-prefixes="java">
<xsl:output method="text"/>

<xsl:template match="/">
<xsl:text>Klass,Förening,Startnr,Namn,Starttid</xsl:text>
<xsl:text>
</xsl:text>
<xsl:apply-templates select="/competition/classes/class"/>
</xsl:template>

<xsl:template match="class">
  <xsl:apply-templates select="registration[startnumber]">
    <xsl:sort select="*[name()='startnumber']" data-type="number" order="ascending"/>
  </xsl:apply-templates>
</xsl:template>

<xsl:template match="registration">
    <xsl:variable name="class" select="parent::*/@name"/>
    <xsl:variable name="starttime" select="starttime"/>
    <xsl:variable name="sstarttime" select="java:cma.util.Time.interval2String($starttime)"/>
<xsl:value-of select="$class"/>,<xsl:value-of select="club"/>,<xsl:value-of select="startnumber"/>,<xsl:value-of select="name"/>,<xsl:value-of select="$sstarttime"/>
<xsl:text>
</xsl:text>
</xsl:template>

</xsl:stylesheet>
