RDEPENDS_${PN} += "cryptodev"
DEPENDS_remove = "ocf-linux"
DEPENDS += "cryptodev-headers"
FILESEXTRAPATHS := "${THISDIR}/openssl-${PV}"

SRC_URI += "file://0001-remove-double-initialization-of-cryptodev-engine.patch \
	    file://0002-add-support-for-TLS-algorithms-offload.patch \
"

# Digest offloading through cryptodev is not recommended because of the
# performance penalty of the Openssl engine interface. Openssl generates a huge
# number of calls to digest functions for even a small amount of work data.
# For example there are 70 calls to cipher code and over 10000 to digest code
# when downloading only 10 files of 700 bytes each.
# Do not build OpenSSL with cryptodev digest support until engine digest
# interface gets some rework:
CFLAG := "${@'${CFLAG}'.replace('-DUSE_CRYPTODEV_DIGESTS', '')}"

BBCLASSEXTEND = ""
