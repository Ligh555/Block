# 定义保存上次比较的提交哈希的文件
last_commit_file="last_checked_commit.txt"

# 检查文件是否存在，如果不存在，提示首次运行并使用当前最新提交作为参考点
if [ ! -f $last_commit_file ]; then
  echo "首次运行，没有找到上次的提交记录。使用当前最新提交作为参考点。"
  git rev-parse HEAD > $last_commit_file
  exit 0
fi

# 读取上次保存的提交哈希
last_commit=$(cat $last_commit_file)

# 获取当前最新的提交哈希
current_commit=$(git rev-parse HEAD)

# 如果上次的提交和当前最新的提交相同，说明没有新变化
if [ "$last_commit" = "$current_commit" ]; then
  echo "没有新提交，所有模块没有发生变化。"
  exit 0
fi

# 比较上次提交 (last_commit) 和最新提交 (current_commit)
changed_files=$(git diff --name-only $last_commit $current_commit)

# 初始化模块变化标志
module_android_changed=false
module_JavaTest_changed=false
module_base_changed=false

# 检查哪些模块的文件发生了变化
for file in $changed_files; do
  if [[ $file == android/* ]]; then
    module_android_changed=true
  elif [[ $file == JavaTest/* ]]; then
    module_JavaTest_changed=true
  elif [[ $file == base/* ]]; then
    module_base_changed=true
  fi
done

# 输出结果到文件
output_file="module_changes.txt"
echo "模块变化结果:" > $output_file
if [ "$module_android_changed" = true ]; then
  echo "module_android 模块发生了变化" >> $output_file
fi
if [ "$module_JavaTest_changed" = true ]; then
  echo "module_JavaTest 模块发生了变化" >> $output_file
fi
if [ "$module_base_changed" = true ]; then
  echo "module_base 模块发生了变化" >> $output_file
fi

# 更新保存的最新提交哈希
echo $current_commit > $last_commit_file

# 输出提示
echo "比较完成，结果已写入 $output_file"
