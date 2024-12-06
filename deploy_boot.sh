#!/bin/bash

# 设置脚本为严格模式
set -euo pipefail

# 定义日志输出函数
log() {
    echo -e "\033[1;32m[$(date '+%Y-%m-%d %H:%M:%S')] $@\033[0m"
}

# 定义错误处理函数
error_exit() {
    echo -e "\033[1;31m[$(date '+%Y-%m-%d %H:%M:%S')] Error: $@\033[0m" >&2
    exit 1
}

# 部署后端
deploy_backend() {
    log "开始部署后端..."
    cd /root/idea/JeecgBoot/jeecg-boot || error_exit "后端路径不存在！"

    log "执行 Maven 打包..."
    if mvn package; then
        log "Maven 打包完成。"
    else
        error_exit "Maven 打包失败！"
    fi
}

# 部署镜像
deploy_docker() {
    log "开始部署 Docker 镜像..."
    cd /root/idea/JeecgBoot || error_exit "Docker 路径不存在！"

    log "构建 Docker 镜像..."
    if docker-compose build; then
        log "Docker 镜像构建完成。"
    else
        error_exit "Docker 镜像构建失败！"
    fi

    log "启动 Docker 容器..."
    if docker-compose up -d; then
        log "Docker 容器启动成功。"
    else
        error_exit "Docker 容器启动失败！"
    fi
}

# 部署前端
deploy_frontend() {
    log "开始部署前端..."
    cd /root/idea/JeecgBoot/jeecgboot-vue3 || error_exit "前端路径不存在！"

    log "安装依赖..."
    if pnpm install; then
        log "依赖安装完成。"
    else
        error_exit "依赖安装失败！"
    fi

    log "构建前端项目..."
    if pnpm build; then
        log "前端项目构建完成。"
    else
        error_exit "前端项目构建失败！"
    fi
}

# 主流程
main() {
    log "脚本开始执行..."
    deploy_backend
    deploy_docker
    deploy_frontend
    log "脚本执行完成，所有步骤成功！"
}

main "$@"