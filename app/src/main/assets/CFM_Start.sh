if [[ "$(magisk -v | grep -i lite)" != "" ]]
then
  MAGISK_PATH="/data/adb/lite_modules"
else
  MAGISK_PATH="/data/adb/modules"
fi

if [ "$(getprop ro.build.version.release)" -lt "12" ] ; then
  disABpath=${MAGISK_PATH}/Clash_For_Magisk/disable
  rm -f ${disABpath}
else
  ${MAGISK_PATH}/Clash_For_Magisk/scripts/clash.service -s && ${MAGISK_PATH}/Clash_For_Magisk/scripts/clash.tproxy -s
fi