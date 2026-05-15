const { createApp, ref, onMounted, nextTick } = Vue;

const app = createApp({
    setup() {
        // State
        const agents = ref([]);
        const selectedAgent = ref('assistant');
        const config = ref({
            provider: 'loading...',
            model: 'loading...'
        });
        const messages = ref([]);
        const inputMessage = ref('');
        const loading = ref(false);
        const sessionId = ref('');
        const messagesContainer = ref(null);

        const settings = ref({
            toolCallEnabled: true,
            memoryEnabled: true,
            showReasoning: true
        });

        // Methods
        const loadAgents = async () => {
            try {
                const response = await axios.get('/api/agent/agents');
                if (response.data.success) {
                    agents.value = response.data.agents;
                    if (agents.value.length > 0) {
                        selectedAgent.value = agents.value[0].name;
                    }
                }
            } catch (error) {
                console.error('Failed to load agents:', error);
            }
        };

        const loadConfig = async () => {
            try {
                const response = await axios.get('/api/agent/config');
                if (response.data.success) {
                    config.value = response.data.config;
                }
            } catch (error) {
                console.error('Failed to load config:', error);
            }
        };

        const scrollToBottom = async () => {
            await nextTick();
            if (messagesContainer.value) {
                messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
            }
        };

        const formatTime = () => {
            return new Date().toLocaleTimeString('zh-CN', {
                hour: '2-digit',
                minute: '2-digit'
            });
        };

        const sendMessage = async () => {
            if (!inputMessage.value.trim() || loading.value) return;

            const userMessage = {
                role: 'user',
                content: inputMessage.value,
                time: formatTime()
            };
            messages.value.push(userMessage);

            const prompt = inputMessage.value;
            inputMessage.value = '';
            loading.value = true;

            await scrollToBottom();

            try {
                const response = await axios.post('/api/agent/chat', {
                    agentName: selectedAgent.value,
                    prompt: prompt,
                    sessionId: sessionId.value || undefined,
                    toolCallEnabled: settings.value.toolCallEnabled,
                    memoryEnabled: settings.value.memoryEnabled,
                    maxIterations: 0
                });

                if (response.data.success) {
                    // Update session ID
                    if (response.data.sessionId) {
                        sessionId.value = response.data.sessionId;
                    }

                    const agentMessage = {
                        role: 'agent',
                        content: response.data.content,
                        time: formatTime(),
                        tokenUsage: response.data.tokenUsage || null,
                        reasoningTrace: response.data.reasoningTrace || null,
                        iterationsUsed: response.data.iterationsUsed || null
                    };
                    messages.value.push(agentMessage);
                } else {
                    const errorMessage = {
                        role: 'agent',
                        content: '❌ 错误: ' + (response.data.message || '未知错误'),
                        time: formatTime()
                    };
                    messages.value.push(errorMessage);
                }
            } catch (error) {
                console.error('Chat failed:', error);
                const errorMessage = {
                    role: 'agent',
                    content: '❌ 请求失败: ' + (error.response?.data?.message || error.message),
                    time: formatTime()
                };
                messages.value.push(errorMessage);
            } finally {
                loading.value = false;
                await scrollToBottom();
            }
        };

        const clearChat = () => {
            messages.value = [];
            sessionId.value = '';
        };

        const clearInput = () => {
            inputMessage.value = '';
        };

        const getStepType = (type) => {
            const typeMap = {
                'THOUGHT': 'warning',
                'ACTION': 'primary',
                'OBSERVATION': 'success',
                'FINAL_ANSWER': 'info'
            };
            return typeMap[type] || 'info';
        };

        // Lifecycle
        onMounted(() => {
            loadAgents();
            loadConfig();
        });

        return {
            agents,
            selectedAgent,
            config,
            messages,
            inputMessage,
            loading,
            settings,
            messagesContainer,
            sendMessage,
            clearChat,
            clearInput,
            getStepType
        };
    }
});

app.use(ElementPlus);
app.mount('#app');
