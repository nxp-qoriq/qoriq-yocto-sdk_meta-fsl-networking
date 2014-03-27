SECTION = "devel"
SUMMARY = "Linux Cryptodev KERNEL MODULE"
DESCRIPTION = "/dev/crypto ioctl interface to Linux kernel crypto operations"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit module

SRC_URI = "git://git.am.freescale.net/gitolite/sdk/cryptodev-linux.git;branch=sdk-v1.6"
SRCREV = "330a7faac655d1d0a94f3b95be8939f8b68d0a66"

EXTRA_OEMAKE='KERNEL_DIR="${STAGING_KERNEL_DIR}" PREFIX="${D}"'

S = "${WORKDIR}/git"
python () {
	ma = d.getVar("DISTRO_FEATURES", True)
	arch = d.getVar("OVERRIDES", True)

	# the : after the arch is to skip the message on 64b
	if not "multiarch" in ma and ("e5500:" in arch or "e6500:" in arch):
		raise bb.parse.SkipPackage("Building the kernel for this arch requires multiarch to be in DISTRO_FEATURES")

	promote_kernel = d.getVar('BUILD_64BIT_KERNEL')

	if promote_kernel == "1":
		d.setVar('KERNEL_CC_append', ' -m64')
		d.setVar('KERNEL_LD_append', ' -melf64ppc')

	error_qa = d.getVar('ERROR_QA', True)
	if 'arch' in error_qa:
		d.setVar('ERROR_QA', error_qa.replace(' arch', ''))
}

do_install_append () {
    rm -fr ${D}/usr
}

FILES_${PN}-dbg += "${bindir}/tests_cryptodev/.debug"
FILES_${PN} += "${bindir}/tests_cryptodev/*"
