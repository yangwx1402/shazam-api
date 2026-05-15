#!/bin/bash
# =======================================================
# Shazam AI Agent 环境变量设置脚本 (macOS/Linux)
# =======================================================
# 使用方法:
#   方式 1 (临时): source set-env.sh
#   方式 2 (永久): 将内容添加到 ~/.zshrc 或 ~/.bashrc
# =======================================================

# 获取脚本所在目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# 加载 .env 文件
if [ -f "$SCRIPT_DIR/.env" ]; then
    echo "✓ 加载 .env 文件..."
    export $(grep -v '^#' "$SCRIPT_DIR/.env" | xargs)
else
    echo "✗ 警告：.env 文件不存在!"
    echo "  请执行：cp $SCRIPT_DIR/.env.example $SCRIPT_DIR/.env"
    echo "  然后编辑 .env 文件填入你的 API Key"
    return 1 2>/dev/null || exit 1
fi

# 验证必要的环境变量
if [ -z "$OPENAI_API_KEY" ]; then
    echo "✗ 错误：OPENAI_API_KEY 未设置!"
    echo "  请在 .env 文件中配置 OPENAI_API_KEY"
    return 1 2>/dev/null || exit 1
fi

# 设置默认值
export OPENAI_MODEL=${OPENAI_MODEL:-gpt-4o-mini}
export OPENAI_TEMPERATURE=${OPENAI_TEMPERATURE:-0.7}
export AGENT_PROVIDER=${AGENT_PROVIDER:-openai}
export AGENT_MODEL=${AGENT_MODEL:-gpt-4o-mini}
export SERVER_PORT=${SERVER_PORT:-8080}

echo "✓ 环境变量设置完成!"
echo ""
echo "=== 当前配置 ==="
echo "OPENAI_API_KEY: ${OPENAI_API_KEY:0:10}... (已隐藏)"
echo "OPENAI_MODEL:   $OPENAI_MODEL"
echo "AGENT_PROVIDER: $AGENT_PROVIDER"
echo "AGENT_MODEL:    $AGENT_MODEL"
echo "SERVER_PORT:    $SERVER_PORT"
echo "================"
echo ""
echo "现在可以运行：mvn spring-boot:run"
