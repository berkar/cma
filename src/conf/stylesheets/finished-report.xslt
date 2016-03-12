<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:template match="/">
  <html>
    <head>
      <title>Slutrapport <xsl:value-of select="//common/name"/>, <xsl:value-of select="//common/date"/></title>
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
      <!--<link rel="stylesheet" type="text/css" href="cma.css"/>-->
      <style>
      body
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small
      }
      p
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small
      }
      h1
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      h2
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      h3
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      h4
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      h5
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      h6
      {
        font-family: Verdana, Arial, Helvetica, sans-serif
      }
      tt
      {
        font-family: "Courier New", Courier, mono; font-size: x-small
      }
      li
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small
      }
      th
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small;
        font-weight: bold;
        text-align: left;
        background-color: #EEEEEE;

        border: silver solid;
              border-left: 1px white solid;
              border-top: 1px white solid;
              border-right: 1px silver solid;
              border-bottom: 1px silver solid
      }
      td
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small
      }
      caption
      {
        font-family: Verdana, Arial, Helvetica, sans-serif;
        font-size: x-small
      }
      a
      {
        text-decoration: none;
        color: #000000;
        cursor:hand
      }
      a:hover
      {
        background-color: #FFFFCC;
        cursor:hand
      }
      a:active
      {
            COLOR: #000000;
            BACKGROUND-COLOR: #ffffcc;
        cursor:hand;
      }
      a:selectedNode
      {

            COLOR: #000000;
            BACKGROUND-COLOR: #ffffcc
        cursor:hand;

      }
      </style>
    </head>
    <body>
      <h3 align="center">Slutrapport <xsl:value-of select="//common/name"/>, <xsl:value-of select="//common/date"/></h3>
      <table width="100%" border="1">
          <tr>
            <th width="25%">Klassnamn</th>
            <th>Antal deltagare</th>
          </tr>
          <xsl:apply-templates select="/competition/classes/class"/>
      </table>
    </body>
  </html>
</xsl:template>

<xsl:template match="class">
  <xsl:if test="count(registration[starting='true']) > 0">
    <tr>
        <td>
            <strong>
                <xsl:value-of select="@name"/>, <xsl:value-of select="@distance"/>km
            </strong>
        </td>
        <td>
            <xsl:value-of select="count(registration[starting='true'])"/>
        </td>
    </tr>
  </xsl:if>
</xsl:template>

</xsl:stylesheet>
