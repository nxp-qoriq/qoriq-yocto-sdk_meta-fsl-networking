DESCRIPTION = "CEETM TC QDISC"
SECTION = "ceetm"
LICENSE = "GPLv2 & BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=b5881ecf398da8a03a3f4c501e29d287"

DEPENDS="virtual/kernel"

inherit module

SRC_URI = "${URL-CEETM}"
SRC_URI += "file://0001-Fix-compile-issue.patch"
SRCREV =  "${SHA-CEETM}"

S = "${WORKDIR}/git"

EXTRA_OEMAKE = "CROSS_COMPILE=${TARGET_PREFIX} SYSROOT=${STAGING_DIR_TARGET}"
export KERNEL_PATH = "${STAGING_KERNEL_DIR}"

python () {
    ma = d.getVar("DISTRO_FEATURES", True)
    arch = d.getVar("OVERRIDES", True)

    # the : after the arch is to skip the message on 64b
    if not "multiarch" in ma and "e6500:" in arch:
        raise bb.parse.SkipPackage("Building the kernel for this arch requires multiarch to be in DISTRO_FEATURES")

    promote_kernel = d.getVar('BUILD_64BIT_KERNEL')

    if promote_kernel == "1":
        d.setVar('KERNEL_CC_append', ' -m64')
        d.setVar('KERNEL_LD_append', ' -melf64ppc')

    error_qa = d.getVar('ERROR_QA', True)
    if 'arch' in error_qa:
        d.setVar('ERROR_QA', error_qa.replace(' arch', ''))
}

do_install(){
	mkdir -p ${D}/usr/driver/ceetm
	mkdir -p ${D}/${libdir}/tc
	cp ${S}/bin/ceetm.ko ${D}/usr/driver/ceetm
	cp ${S}/bin/q_ceetm.so ${D}/${libdir}/tc/.
}

FILES_${PN} += "/usr/driver/ceetm ${libdir}/tc"
INHIBIT_PACKAGE_STRIP = "1"
