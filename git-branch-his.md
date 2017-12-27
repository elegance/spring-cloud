#### 1-01 
```bash
git checkout -b 1-01 master # 建立出分支
git checkout master # 切回master
git push -u origin master # 推送到远端master
git checkout 1-01 # 切回1-01
git push origin 1-01:1-01 # 推送分支 1-01 至远端，不存在则会创建远端分支
```

#### 1-02
```bash
git checkout -b 1-02 master # 1-02分支
git push origin 1-02
```

#### 1-03
```bash
git checkout -b 1-03 1-02 # 在 1-02 分支上建立 1-03
git push origin 1-03
```

#### 1-04
```bash
git checkout -b 1-04 1-03
git push origin 1-04
```

#### 1-05
```bash
git checkout -b 1-05 1-04
git push origin 1-05
```

#### 1-06
```bash
git checkout -b 1-06 1-05
git checkout master #切回master
git merge 1-06 #将分支内容合并到 master
git checkout 1-06
git push origin 1-06
```

#### 1-07
```bash
git checkout -b 1-07 1-06
git push origin 1-07
```

#### 1-08
```bash
git checkout -b 1-08 1-07
git push origin 1-08
```

#### 1-09
```bash
git checkout -b 1-09 1-08
git push origin 1-09
```

#### 1-10
```bash
git checkout -b 1-10 1-09
git push origin 1-10
```

#### 2-01
```bash
git checkout -b 2-01 1-10
git push origin 2-01
```

#### 2-02
```bash
git checkout -b 2-02 2-01
git push origin 2-02
```

#### 2-03
```bash
git checkout -b 2-03 2-02
git push origin 2-03
```

#### 2-04
```bash
git checkout -b 2-04 2-03
git push origin 2-04
```

#### 2-05
```bash
git checkout -b 2-05 2-04
git push origin 2-05
```

#### 2-06
```bash
git checkout -b 2-06 2-05
git push origin 2-06
```

#### 2-07
```bash
git checkout -b 2-07 2-06
git push origin 2-07
git checkout master
git merge 2-07 # 2-07 的修改合并到 master
```

#### 2-08
```bash
git checkout -b 2-08 master # 基于master 建立 2-08
git push origin 2-08
```

#### 2-09
```bash
git checkout -b 2-09 2-08
git push origin 2-09
```

#### 2-10
```bash
git checkout -b 2-10 2-09
git push origin 2-10
git checkout master
git merge 2-10 # 将 2-10 合回 master
```

#### 3-01
```bash
git checkout -b 3-01 master
git push origin 3-01
```

#### 3-02
```bash
git checkout -b 3-02 3-01
git push origin 3-02
```

#### 3-03
```bash
git checkout -b 3-03 3-02
git push origin 3-03
```

#### 3-04
```bash
git checkout -b 3-04 3-03
git push origin 3-04
```

#### 3-05
```bash
git checkout -b 3-05 3-04
git push origin 3-05
```

#### 3-06
```bash
git checkout -b 3-06 3-05
git push origin 3-06
```

#### 3-07
```bash
git checkout -b 3-07 3-06
git push origin 3-07
```

#### 3-08
```bash
git checkout -b 3-08 3-07
git push origin 3-08
git checkout master
git merge origin-3-08
```