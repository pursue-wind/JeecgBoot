#!/bin/bash

# 设置脚本严格模式
set -euo pipefail

# 定义日志函数
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
        log "Maven 打包成功。"
    else
        error_exit "Maven 打包失败！"
    fi

    # 部署 Docker 镜像和容器
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
        log "前端项目构建成功。"
    else
        error_exit "前端项目构建失败！"
    fi

    log "开始部署前端到 Nginx..."
    log "备份当前 Nginx html 文件夹..."
    if tar -zcvf /var/www/html/html.tar.gz /var/www/html/; then
        log "备份完成。"
    else
        error_exit "备份失败！"
    fi

    log "拷贝前端构建产物到 /var/www/html/..."
    if cp -r /root/idea/JeecgBoot/jeecgboot-vue3/dist/* /var/www/html/; then
        log "前端文件拷贝完成。"
    else
        error_exit "文件拷贝失败！"
    fi

    log "测试 Nginx 配置..."
    if nginx -t; then
        log "Nginx 配置测试通过。"
    else
        error_exit "Nginx 配置测试失败！"
    fi

    log "重载 Nginx 服务..."
    if nginx -s reload; then
        log "Nginx 重载成功。"
    else
        error_exit "Nginx 重载失败！"
    fi
}

# 帮助信息
show_help() {
    echo "Usage: $0 [--backend] [--frontend] [--all]"
    echo "Options:"
    echo "  --backend   只构建和部署后端"
    echo "  --frontend  只构建和部署前端"
    echo "  --all       同时构建和部署前端与后端（默认）"
}

# 主流程
main() {
    # 检查参数
    if [[ $# -eq 0 ]]; then
        show_help
        exit 1
    fi

    local build_backend=false
    local build_frontend=false

    # 解析参数
    for arg in "$@"; do
        case $arg in
            --backend)
                build_backend=true
                ;;
            --frontend)
                build_frontend=true
                ;;
            --all)
                build_backend=true
                build_frontend=true
                ;;
            *)
                log "未知参数：$arg"
                show_help
                exit 1
                ;;
        esac
    done

    log "脚本开始执行..."

    # 根据参数执行对应构建
    if $build_backend; then
        deploy_backend
    fi

    if $build_frontend; then
        deploy_frontend
    fi

    log "所有部署步骤完成，脚本执行成功！"
}

# 调用主函数
main "$@"

#./deploy.sh --backend
#./deploy.sh --frontend
#./deploy.sh --all