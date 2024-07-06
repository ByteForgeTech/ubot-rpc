// 聊天互通附属

// 监听游戏事件
function onGameEvent(event, type) {
    // check if message event
    if (type !== "AsyncPlayerChatEvent") {
        return;
    }
    var message = event.getMessage();
    ScriptHelper.sendRPCMessage("JS接收到: " + message);
}

// 获取脚本版本
function getVersion() {
    return "20240630-0";
}