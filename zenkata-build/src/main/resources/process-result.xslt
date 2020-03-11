<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
    <xsl:output method="xml" encoding="UTF-8"/>
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="result-group">  
        <result-group>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates select="node()"/>
        </result-group>
    </xsl:template>
    <xsl:template match="result-file">  
        <result-file>
            <xsl:copy-of select="@*"/>
            --BEGIN-CDATA--
            <xsl:apply-templates/>
            --END-CDATA--
        </result-file>
    </xsl:template>
</xsl:stylesheet>
