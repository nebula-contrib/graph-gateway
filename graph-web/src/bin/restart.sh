#! /bin/shell

#======================================================================
# 项目重启shell脚本
# 先调用shutdown.sh停服
# 然后调用startup.sh启动服务
#
# author: lpz
# date: 2019-12-2
#======================================================================

# 项目名称
APPLICATION="graph-web"

# 停服
echo stop ${APPLICATION} Application...
sh shutdown.sh
sleep 2

# 启动服务
echo start ${APPLICATION} Application...
sh startup.sh