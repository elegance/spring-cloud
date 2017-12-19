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