DESCRIPTION = "Mux server"
SECTION = "mux-server"
LICENSE = "LGPL"
PR = "r1"
LIC_FILES_CHKSUM = "file://mux_server.c;endline=9;md5=e59eeb0812bb88b7af2d932f2dc22aed"

SRC_URI = "file://mux-server-${PV}.tar.gz;name=mux_server"

EXTRA_OEMAKE='HOSTCC="${CC}"'

do_install () {
        install -d ${D}${bindir}
        install -m 755 mux_server ${D}${bindir}
}

BBCLASSEXTEND = "native nativesdk"
